package workout.home.homeworkout.model

data class ExerciseModel(
    var id: Int = 0,
    var name: String = "",
    var image: String = "",
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
) {
    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}

