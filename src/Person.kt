data class Person (val name: String, val role: String) {
    override fun toString(): String {
        return "\bПользователь: $name, $role"
    }
}