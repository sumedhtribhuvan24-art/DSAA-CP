import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Knapsack {
    static Result solve(List<Task> tasks, int availableMinutes) {
        int n = tasks.size();
        int[][] dp = new int[n + 1][availableMinutes + 1];

        for (int i = 1; i <= n; i++) {
            Task task = tasks.get(i - 1);
            for (int time = 0; time <= availableMinutes; time++) {
                dp[i][time] = dp[i - 1][time];
                if (task.duration <= time) {
                    dp[i][time] = Math.max(
                        dp[i][time],
                        task.value + dp[i - 1][time - task.duration]
                    );
                }
            }
        }

        List<Task> chosenTasks = new ArrayList<>();
        int time = availableMinutes;
        for (int i = n; i >= 1; i--) {
            if (dp[i][time] != dp[i - 1][time]) {
                Task task = tasks.get(i - 1);
                chosenTasks.add(task);
                time -= task.duration;
            }
        }

        Collections.reverse(chosenTasks);
        return new Result(dp[n][availableMinutes], chosenTasks);
    }

    static class Result {
        int maxValue;
        List<Task> chosenTasks;

        Result(int maxValue, List<Task> chosenTasks) {
            this.maxValue = maxValue;
            this.chosenTasks = chosenTasks;
        }
    }
}
