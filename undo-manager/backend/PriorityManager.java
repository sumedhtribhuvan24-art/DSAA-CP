import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class PriorityManager {
    private final PriorityQueue<Task> pq = new PriorityQueue<>(
        (a, b) -> {
            if (b.priority != a.priority) {
                return b.priority - a.priority;
            }
            return a.duration - b.duration;
        }
    );

    void addTask(Task t) {
        pq.add(t.copy());
    }

    Task getTop() {
        return pq.poll();
    }

    void loadTasks(List<Task> tasks) {
        pq.clear();
        for (Task task : tasks) {
            addTask(task);
        }
    }

    List<Task> getExecutionOrder() {
        PriorityQueue<Task> copy = new PriorityQueue<>(pq);
        List<Task> orderedTasks = new ArrayList<>();
        while (!copy.isEmpty()) {
            orderedTasks.add(copy.poll());
        }
        return orderedTasks;
    }
}
