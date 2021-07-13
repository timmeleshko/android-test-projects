package by.senla.timmeleshko

import dagger.Component
import javax.inject.Inject

@Component
interface DaggerComponent {
    fun getCar(): Car
    fun getEngine(): Engine
    fun getFuel(): Fuel
}

/* Определяет вводимые конструкторы, методы и поля. Может применяться как к статическим, так и к экземплярам.
Вводимый член может иметь любой модификатор доступа (private, package-private, protected, public). Сначала
вводятся конструкторы, затем поля, а затем методы. Поля и методы в суперклассах вводятся раньше, чем в
подклассах. Порядок внедрения между полями и методами в одном классе не указан. Инъекционные конструкторы
помечаются @Inject и принимают ноль или более зависимостей в качестве аргументов. @Inject может применяться
не более чем к одному конструктору на класс.
*/
data class Car @Inject constructor(val engine: Engine)
data class Engine @Inject constructor(val fuel: Fuel)
class Fuel @Inject constructor() {
    private val fuelType = if (BuildConfig.DEBUG) {
        "benzine"
    } else {
        "diesel"
    }

    override fun toString(): String {
        return "fuelType=$fuelType"
    }
}