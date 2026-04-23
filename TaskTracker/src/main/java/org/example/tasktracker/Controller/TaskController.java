package org.example.tasktracker.Controller;

import org.example.tasktracker.ApiResponse.ApiResponse;
import org.example.tasktracker.Model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    ArrayList<Task> tasks = new ArrayList<>();


    //create new task
    @PostMapping("/create-task")
    public ApiResponse createTask(@RequestBody Task task){
        String id;
        boolean isExist;
        do{ //to make sure each id unique
            id = ""+(int) (Math.random() * ((9999 - 1000) + 1));
            isExist = false;

            for(int i=0; i < tasks.size(); i++){
                if(tasks.get(i).getId().equals(id)){
                    isExist = true;
                    break;
                }
            }

        } while (isExist);

        task.setId(id); //set generated unique id of four numbers
        tasks.add(task);
        return new ApiResponse("Task created successfully");
    }

    //display all tasks
    @GetMapping("/display-tasks")
    public ArrayList<Task> displayTasks(){
        return tasks;
    }

    //update task
    @PutMapping("/update-task/{id}")
    public ApiResponse updateTask(@PathVariable String id,@RequestBody Task newTask){
        for(int i=0; i < tasks.size(); i++){
            if(tasks.get(i).getId().equals(id)){
                newTask.setId(id);
                tasks.set(i, newTask);
                return new ApiResponse("Task updated successfully");
            }
        }
        return new ApiResponse("Sorry can't update task not found");
    }

    //delete task
    @DeleteMapping("/delete-task/{id}")
    public ApiResponse deleteTask(@PathVariable String id){
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getId().equals(id)){
                tasks.remove(i);
                return new ApiResponse("Task deleted successfully");
            }
        }
        return new ApiResponse("Sorry can't delete task not found");
    }

    //change task status as done or not done
    @PutMapping("/change-status/{id}/{status}")
    public ApiResponse changeTaskStatus(@PathVariable String id,@PathVariable String status){
        if(!status.equals("done") && !status.equals("not done"))
            return new ApiResponse("Status should be either done or not done");
        for(Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                return new ApiResponse("Status changed successfully");
            }
        }
        return new ApiResponse("Sorry can't change status task not found");
    }

    //search for task by title
    @GetMapping("/search-by-title/{title}")
    public ArrayList<Task> searchTaskByTitle(@PathVariable String title){
        ArrayList<Task> tasksByTitle = new ArrayList<>();
        for(Task task : tasks){
            if(task.getTitle().equals(title)){
                tasksByTitle.add(task);
            }
        }
        return tasksByTitle;
    }
}
