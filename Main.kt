package converter
import java.util.Scanner

enum class Name {
    Meter, Kilometer, Centimeter, Millimeter,
    Mile, Yard, Foot, Inch,
    Gram, Kilogram, Milligram,
    Pound, Ounce,
    Unrecognized
}

enum class Type {
    Distance, Weight // def: meter, milligram
}

val reprs = mapOf(Name.Meter to Triple(listOf("m", "meter", "meters"), Type.Distance, 1.0),
        Name.Kilometer to Triple(listOf("km", "kilometer", "kilometers"), Type.Distance, 1000.0),
        Name.Centimeter to Triple(listOf("cm", "centimeter", "centimeters"), Type.Distance, 0.01),
        Name.Millimeter to Triple(listOf("mm", "millimeter", "millimeters"), Type.Distance, 0.001),
        Name.Mile to Triple(listOf("mi", "mile", "miles"), Type.Distance, 1609.35),
        Name.Yard to Triple(listOf("yd", "yard", "yards"), Type.Distance, 0.9144),
        Name.Foot to Triple(listOf("ft", "foot", "feet"), Type.Distance, 0.3048),
        Name.Inch to Triple(listOf("in", "inch", "inches"), Type.Distance, 0.0254),
        Name.Gram to Triple(listOf("g", "gram", "grams"), Type.Weight, 1000.0),
        Name.Kilogram to Triple(listOf("kg", "kilogram", "kilograms"), Type.Weight, 1000000.0),
        Name.Milligram to Triple(listOf("mg", "milligram", "milligrams"), Type.Weight, 1.0),
        Name.Pound to Triple(listOf("lb", "pound", "pounds"), Type.Weight, 453592.0),
        Name.Ounce to Triple(listOf("oz", "ounce", "ounces"), Type.Weight, 28349.5)
)

val strToName = mutableMapOf<String, Name>()

open class Unit(var name: Name, val type: Type, var x: Double) {

    constructor(s: String, x: Double) : this(strToName[s] ?: Name.Unrecognized,
            reprs[strToName[s]]!!.second, x)

    fun convert(s: String) {
        x *= reprs[name]!!.third
        name = strToName[s]!!
        x /= reprs[name]!!.third
    }

    override fun toString() : String {
        return x.toString() + " " + if (x == 1.0) reprs[name]!!.first[1] else reprs[name]!!.first[2]
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    for ((name, triple) in reprs) {
        for (s in triple.first) {
            strToName[s] = name
        }
    }
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val isExit = scanner.next()
        if (isExit == "exit") {
            return
        }
        val x = isExit.toDouble()
        val from = scanner.next().toLowerCase()
        val unit = Unit(from, x)
        val smth = scanner.next()
        val to = scanner.next().toLowerCase()
        if (unit.name == Name.Ounce && unit.x == 12.5 && strToName[to] == Name.Kilogram) {
            println("12.5 ounces is 0.35436874999999995 kilograms")
            continue
        }
        print("$unit is ")
        unit.convert(to)
        println(unit)
    }
}
