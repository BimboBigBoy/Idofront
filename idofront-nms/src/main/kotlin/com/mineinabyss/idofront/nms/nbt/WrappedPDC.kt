package com.mineinabyss.idofront.nms.nbt

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_19_R1.persistence.CraftPersistentDataAdapterContext
import org.bukkit.craftbukkit.v1_19_R1.persistence.CraftPersistentDataTypeRegistry
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

/**
 * A [PersistentDataContainer] that takes an NMS NBT tag. Useful for avoiding bukkit's
 * constant copying (ex on ItemStacks.)
 */
class WrappedPDC(
    val compoundTag: CompoundTag
) : PersistentDataContainer {
    private val adapterContext = CraftPersistentDataAdapterContext(DATA_TYPE_REGISTRY)

    override fun <T, Z : Any> set(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) {
        compoundTag.put(
            key.toString(),
            DATA_TYPE_REGISTRY.wrap(type.primitiveType, type.toPrimitive(value, adapterContext))
        )
    }

    override fun <T, Z> has(key: NamespacedKey, type: PersistentDataType<T, Z>): Boolean =
        compoundTag.contains(key.toString())

    override fun has(key: NamespacedKey): Boolean =
        compoundTag.contains(key.toString())

    override fun <T : Any, Z> get(key: NamespacedKey, type: PersistentDataType<T, Z>): Z? {
        val value: Tag = compoundTag.get(key.toString()) ?: return null
        return type.fromPrimitive(DATA_TYPE_REGISTRY.extract(type.primitiveType, value), adapterContext)
    }

    override fun <T : Any, Z : Any> getOrDefault(
        key: NamespacedKey,
        type: PersistentDataType<T, Z>,
        defaultValue: Z
    ): Z = get(key, type) ?: defaultValue

    override fun getKeys(): MutableSet<NamespacedKey> =
        compoundTag.allKeys.mapTo(mutableSetOf()) { NamespacedKey.fromString(it)!! }

    override fun remove(key: NamespacedKey) {
        compoundTag.remove(key.toString())
    }

    override fun isEmpty(): Boolean = compoundTag.isEmpty

    override fun getAdapterContext(): PersistentDataAdapterContext = adapterContext

    companion object {
        private val DATA_TYPE_REGISTRY = CraftPersistentDataTypeRegistry()
    }
}