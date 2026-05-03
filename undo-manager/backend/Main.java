import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Task> allTasks = new ArrayList<>(Arrays.asList(
            new Task(101, "Prepare DSA presentation", "College", 5, 90, "Library", 10),
            new Task(102, "Buy groceries", "Personal", 2, 40, "Market", 4),
            new Task(103, "Submit lab record", "College", 4, 60, "Lab", 8),
            new Task(104, "Gym session", "Health", 3, 45, "Gym", 6),
            new Task(105, "Meet project guide", "College", 5, 30, "Faculty Block", 9)
        ));

        System.out.println("SMART TASK MANAGER - DSA PROJECT");
        System.out.println();

        demoStackUndoRedo(allTasks);
        demoHeapPriorityScheduling(allTasks);
        demoHashMapLookup(allTasks);
        demoTrieSuggestions(allTasks);
        demoGraphShortestPath();
        demoKnapsackOptimization(allTasks);
    }

    private static void demoStackUndoRedo(List<Task> seedTasks) {
        System.out.println("1. STACK - Undo / Redo");
        List<Task> workingList = new ArrayList<>();
        UndoManager undoManager = new UndoManager();

        undoManager.saveState(workingList);
        workingList.add(seedTasks.get(0).copy());
        undoManager.saveState(workingList);
        workingList.add(seedTasks.get(1).copy());
        undoManager.saveState(workingList);
        workingList.remove(1);
        undoManager.saveState(workingList);

        System.out.println("Current tasks after delete: " + workingList);
        workingList = undoManager.undo(workingList);
        System.out.println("After undo: " + workingList);
        workingList = undoManager.redo(workingList);
        System.out.println("After redo: " + workingList);
        System.out.println("Real life use: if user deletes a task by mistake, stack brings it back instantly.");
        System.out.println();
    }

    private static void demoHeapPriorityScheduling(List<Task> tasks) {
        System.out.println("2. HEAP - Priority Scheduling");
        PriorityManager priorityManager = new PriorityManager();
        priorityManager.loadTasks(tasks);

        List<Task> executionOrder = priorityManager.getExecutionOrder();
        for (Task task : executionOrder) {
            System.out.println(task);
        }
        System.out.println("Real life use: urgent college work is shown before low priority personal work.");
        System.out.println();
    }

    private static void demoHashMapLookup(List<Task> tasks) {
        System.out.println("3. HASHMAP - Fast Task Lookup");
        Map<Integer, Task> taskIndex = new HashMap<>();
        for (Task task : tasks) {
            taskIndex.put(task.id, task);
        }

        int taskIdToFind = 103;
        Task foundTask = taskIndex.get(taskIdToFind);
        System.out.println("Task with ID " + taskIdToFind + ": " + foundTask);
        System.out.println("Real life use: when user opens a task by ID, HashMap gives instant access.");
        System.out.println();
    }

    private static void demoTrieSuggestions(List<Task> tasks) {
        System.out.println("4. TRIE - Search Suggestions");
        Trie trie = new Trie();
        for (Task task : tasks) {
            trie.insert(task.name);
        }

        String prefix = "Pr";
        System.out.println("Suggestions for prefix '" + prefix + "': " + trie.suggest(prefix, 5));
        System.out.println("Real life use: typing 'Pr' can suggest 'Prepare DSA presentation' quickly.");
        System.out.println();
    }

    private static void demoGraphShortestPath() {
        System.out.println("5. GRAPH + DIJKSTRA - Shortest Route");
        Graph graph = new Graph();
        graph.addEdge("Home", "Library", 4);
        graph.addEdge("Home", "Market", 7);
        graph.addEdge("Library", "Lab", 3);
        graph.addEdge("Library", "Faculty Block", 6);
        graph.addEdge("Lab", "Faculty Block", 2);
        graph.addEdge("Market", "Gym", 5);
        graph.addEdge("Faculty Block", "Gym", 4);

        Graph.RouteResult route = graph.shortestPath("Home", "Faculty Block");
        System.out.println("Best route: " + route.path + " with total distance " + route.distance);
        System.out.println("Real life use: student can plan the shortest path to finish campus tasks faster.");
        System.out.println();
    }

    private static void demoKnapsackOptimization(List<Task> tasks) {
        System.out.println("6. DYNAMIC PROGRAMMING - Time Optimization");
        int availableMinutes = 180;
        Knapsack.Result result = Knapsack.solve(tasks, availableMinutes);

        System.out.println("Available time: " + availableMinutes + " minutes");
        System.out.println("Best value: " + result.maxValue);
        System.out.println("Selected tasks: " + result.chosenTasks);
        System.out.println("Real life use: choose the most valuable tasks when time is limited.");
        System.out.println();
    }
}
