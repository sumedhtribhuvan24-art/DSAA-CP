import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

class UndoManager {
    private final Stack<List<Task>> undoStack = new Stack<>();
    private final Stack<List<Task>> redoStack = new Stack<>();

    void saveState(List<Task> tasks) {
        undoStack.push(copyTasks(tasks));
        redoStack.clear();
    }

    List<Task> undo(List<Task> currentTasks) {
        if (undoStack.size() <= 1) {
            return copyTasks(currentTasks);
        }

        redoStack.push(copyTasks(currentTasks));
        undoStack.pop();
        return copyTasks(undoStack.peek());
    }

    List<Task> redo(List<Task> currentTasks) {
        if (redoStack.isEmpty()) {
            return copyTasks(currentTasks);
        }

        List<Task> restored = copyTasks(redoStack.pop());
        undoStack.push(copyTasks(restored));
        return restored;
    }

    private List<Task> copyTasks(List<Task> tasks) {
        List<Task> snapshot = new ArrayList<>();
        for (Task task : tasks) {
            snapshot.add(task.copy());
        }
        return snapshot;
    }
}
