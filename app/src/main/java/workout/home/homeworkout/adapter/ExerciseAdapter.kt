package workout.home.homeworkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import workout.home.homeworkout.databinding.ItemExerciseBinding
import workout.home.homeworkout.model.ExerciseModel


class ExerciseAdapter(
    private val requireContext: Context,
    private val listExerciseDetail: ArrayList<ExerciseModel>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    inner class ExerciseViewHolder(val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(LayoutInflater.from(requireContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        Glide.with(requireContext).asGif().load(listExerciseDetail[position].image)
            .into(holder.binding.imgExercise)

        holder.binding.tvExercise.text = listExerciseDetail[position].name

    }

    override fun getItemCount(): Int {
        return listExerciseDetail.size
    }
}
