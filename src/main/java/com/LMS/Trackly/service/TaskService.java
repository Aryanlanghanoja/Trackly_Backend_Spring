package com.LMS.Trackly.service;

import com.LMS.Trackly.model.Task;
import com.LMS.Trackly.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    // Get tasks by followup ID
    public List<Task> getTasksByFollowupId(Integer followupId) {
        return taskRepository.findByFollowUpFollowUpId(followupId);
    }

    // Get tasks by deadline range
    public List<Task> getTasksByDeadlineRange(Date startDate, Date endDate) {
        return taskRepository.findByDeadlineBetween((java.sql.Date) startDate, (java.sql.Date) endDate);
    }

    // Create new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Update task
    public Task updateTask(Integer id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found for id :: " + id));

        // Update fields
        task.setDeadline(taskDetails.getDeadline());
        task.setDescription(taskDetails.getDescription());
        task.setFollowUp(taskDetails.getFollowUp());

        return taskRepository.save(task);
    }

    // Delete task
    public boolean deleteTask(Integer id) {
        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found for id :: " + id));
            taskRepository.delete(task);
            return true;
        } catch (Exception e) {
            // Log error if needed
            return false;
        }
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByAssignedTo(String username) {
        return taskRepository.findTasksByLeadAssignedTo(username);
    }


    public Optional<Task> getTask(Integer id) {
        return taskRepository.findById(id);
    }

}

