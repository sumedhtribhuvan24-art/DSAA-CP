let tasks = [
    {
        id: 101,
        name: "Prepare DSA presentation",
        category: "College",
        location: "Library",
        duration: 90,
        priority: 5
    },
    {
        id: 102,
        name: "Buy groceries",
        category: "Personal",
        location: "Market",
        duration: 40,
        priority: 2
    },
    {
        id: 103,
        name: "Submit lab record",
        category: "College",
        location: "Lab",
        duration: 60,
        priority: 4
    }
];

let undoStack = [];
let redoStack = [];
let nextTaskId = 104;

const campusGraph = {
    Home: [
        { node: "Library", weight: 4 },
        { node: "Market", weight: 7 }
    ],
    Library: [
        { node: "Home", weight: 4 },
        { node: "Lab", weight: 3 },
        { node: "Faculty Block", weight: 6 }
    ],
    Lab: [
        { node: "Library", weight: 3 },
        { node: "Faculty Block", weight: 2 }
    ],
    Market: [
        { node: "Home", weight: 7 },
        { node: "Gym", weight: 5 }
    ],
    "Faculty Block": [
        { node: "Library", weight: 6 },
        { node: "Lab", weight: 2 },
        { node: "Gym", weight: 4 }
    ],
    Gym: [
        { node: "Market", weight: 5 },
        { node: "Faculty Block", weight: 4 }
    ]
};

saveSnapshot();

function addTask() {
    const inputElement = document.getElementById("taskInput");
    const categoryElement = document.getElementById("categoryInput");
    const locationElement = document.getElementById("locationInput");
    const durationElement = document.getElementById("durationInput");
    const priorityElement = document.getElementById("priorityInput");
    const input = inputElement.value.trim();

    if (!input) {
        inputElement.focus();
        return;
    }

    tasks.push({
        id: nextTaskId++,
        name: input,
        category: categoryElement.value.trim() || "General",
        location: locationElement.value.trim() || "Not set",
        duration: Number(durationElement.value) || 30,
        priority: Number(priorityElement.value)
    });

    saveSnapshot();
    redoStack = [];
    inputElement.value = "";
    categoryElement.value = "";
    locationElement.value = "";
    durationElement.value = "";
    priorityElement.value = "3";
    render();
}

function undo() {
    if (undoStack.length <= 1) {
        return;
    }

    redoStack.push(createSnapshot(tasks));
    undoStack.pop();
    tasks = undoStack[undoStack.length - 1].map(task => ({ ...task }));
    render();
}

function redo() {
    if (redoStack.length === 0) {
        return;
    }

    tasks = redoStack.pop().map(task => ({ ...task }));
    saveSnapshot();
    render();
}

function render() {
    const list = document.getElementById("taskList");
    const taskCount = document.getElementById("taskCount");
    list.innerHTML = "";

    if (taskCount) {
        taskCount.innerText = `${tasks.length} ${tasks.length === 1 ? "Task" : "Tasks"}`;
    }

    if (tasks.length === 0) {
        const emptyItem = document.createElement("li");
        emptyItem.className = "empty-state";
        emptyItem.innerText = "No tasks added yet. Start by typing your first task above.";
        list.appendChild(emptyItem);
        return;
    }

    const orderedTasks = getHeapOrderedTasks(tasks);
    orderedTasks.forEach(task => {
        const li = document.createElement("li");
        li.innerHTML = `
            <div class="task-top">
                <p class="task-name">${task.name}</p>
                <span class="priority-badge">Priority ${task.priority}</span>
            </div>
            <div class="task-meta">
                <span>ID ${task.id}</span>
                <span>${task.category}</span>
                <span>${task.location}</span>
                <span>${task.duration} mins</span>
            </div>
        `;
        list.appendChild(li);
    });
}

function saveSnapshot() {
    undoStack.push(createSnapshot(tasks));
}

function createSnapshot(sourceTasks) {
    return sourceTasks.map(task => ({ ...task }));
}

function runHeapDemo() {
    const ordered = getHeapOrderedTasks(tasks);
    const lines = ordered.map((task, index) =>
        `${index + 1}. ${task.name} -> priority ${task.priority}, duration ${task.duration} mins`
    );

    showResults("Heap / Priority Queue", [
        "A max-heap is built from the current tasks.",
        "Tasks are extracted one by one in highest-priority order.",
        ...lines
    ]);
}

function runTrieDemo() {
    const prefix = document.getElementById("searchInput").value.trim();

    if (!prefix) {
        showResults("Trie", ["Enter a prefix like 'Pr' or 'Su' to run the suggestion demo."]);
        return;
    }

    const suggestions = getTrieSuggestions(tasks.map(task => task.name), prefix, 5);

    if (suggestions.length === 0) {
        showResults("Trie", [
            `No task starts with "${prefix}".`,
            "This shows prefix search returning no matching branch."
        ]);
        return;
    }

    showResults("Trie", [
        `Prefix searched: ${prefix}`,
        `Suggestions found: ${suggestions.join(", ")}`
    ]);
}

function runDijkstra() {
    const start = document.getElementById("startLocation").value;
    const end = document.getElementById("endLocation").value;
    const result = shortestPath(campusGraph, start, end);

    if (!result.path.length) {
        showResults("Dijkstra", [`No route found from ${start} to ${end}.`]);
        return;
    }

    showResults("Graph + Dijkstra", [
        `Start location: ${start}`,
        `End location: ${end}`,
        `Shortest path: ${result.path.join(" -> ")}`,
        `Total distance: ${result.distance}`
    ]);
}

function runKnapsack() {
    const timeLimit = Number(document.getElementById("timeLimitInput").value) || 180;
    const result = optimizeTasks(tasks, timeLimit);
    const names = result.selectedTasks.map(task =>
        `${task.name} (${task.duration} mins, value ${task.priority})`
    );

    showResults("Knapsack / Dynamic Programming", [
        `Available time: ${timeLimit} minutes`,
        `Maximum total value: ${result.bestValue}`,
        result.selectedTasks.length
            ? `Selected tasks: ${names.join(", ")}`
            : "No tasks fit inside the given time limit."
    ]);
}

function getHeapOrderedTasks(sourceTasks) {
    const heap = [];

    sourceTasks.forEach(task => heapPush(heap, { ...task }));

    const ordered = [];
    while (heap.length > 0) {
        ordered.push(heapPop(heap));
    }

    return ordered;
}

function heapPush(heap, task) {
    heap.push(task);
    let index = heap.length - 1;

    while (index > 0) {
        const parentIndex = Math.floor((index - 1) / 2);
        if (compareTasks(heap[index], heap[parentIndex]) <= 0) {
            break;
        }

        [heap[index], heap[parentIndex]] = [heap[parentIndex], heap[index]];
        index = parentIndex;
    }
}

function heapPop(heap) {
    if (heap.length === 1) {
        return heap.pop();
    }

    const top = heap[0];
    heap[0] = heap.pop();
    let index = 0;

    while (true) {
        let largest = index;
        const left = 2 * index + 1;
        const right = 2 * index + 2;

        if (left < heap.length && compareTasks(heap[left], heap[largest]) > 0) {
            largest = left;
        }

        if (right < heap.length && compareTasks(heap[right], heap[largest]) > 0) {
            largest = right;
        }

        if (largest === index) {
            break;
        }

        [heap[index], heap[largest]] = [heap[largest], heap[index]];
        index = largest;
    }

    return top;
}

function compareTasks(a, b) {
    if (a.priority !== b.priority) {
        return a.priority - b.priority;
    }

    return b.duration - a.duration;
}

function getTrieSuggestions(words, prefix, limit) {
    const root = createTrieNode();
    words.forEach(word => insertWord(root, word));
    return collectSuggestions(root, prefix, limit);
}

function createTrieNode() {
    return { children: {}, isEnd: false, word: "" };
}

function insertWord(root, word) {
    let node = root;

    for (const char of word.toLowerCase()) {
        if (!node.children[char]) {
            node.children[char] = createTrieNode();
        }
        node = node.children[char];
    }

    node.isEnd = true;
    node.word = word;
}

function collectSuggestions(root, prefix, limit) {
    let node = root;

    for (const char of prefix.toLowerCase()) {
        if (!node.children[char]) {
            return [];
        }
        node = node.children[char];
    }

    const suggestions = [];
    dfsTrie(node, suggestions, limit);
    return suggestions;
}

function dfsTrie(node, suggestions, limit) {
    if (!node || suggestions.length >= limit) {
        return;
    }

    if (node.isEnd) {
        suggestions.push(node.word);
    }

    Object.keys(node.children).sort().forEach(key => {
        if (suggestions.length < limit) {
            dfsTrie(node.children[key], suggestions, limit);
        }
    });
}

function shortestPath(graph, start, end) {
    const distances = {};
    const previous = {};
    const queue = [{ node: start, distance: 0 }];

    Object.keys(graph).forEach(node => {
        distances[node] = Infinity;
        previous[node] = null;
    });
    distances[start] = 0;

    while (queue.length > 0) {
        queue.sort((a, b) => a.distance - b.distance);
        const current = queue.shift();

        if (current.distance > distances[current.node]) {
            continue;
        }

        graph[current.node].forEach(neighbor => {
            const nextDistance = distances[current.node] + neighbor.weight;
            if (nextDistance < distances[neighbor.node]) {
                distances[neighbor.node] = nextDistance;
                previous[neighbor.node] = current.node;
                queue.push({ node: neighbor.node, distance: nextDistance });
            }
        });
    }

    if (distances[end] === Infinity) {
        return { distance: Infinity, path: [] };
    }

    const path = [];
    let currentNode = end;
    while (currentNode) {
        path.unshift(currentNode);
        currentNode = previous[currentNode];
    }

    return { distance: distances[end], path };
}

function optimizeTasks(sourceTasks, timeLimit) {
    const n = sourceTasks.length;
    const dp = Array.from({ length: n + 1 }, () => Array(timeLimit + 1).fill(0));

    for (let i = 1; i <= n; i++) {
        const task = sourceTasks[i - 1];
        const value = task.priority;

        for (let time = 0; time <= timeLimit; time++) {
            dp[i][time] = dp[i - 1][time];
            if (task.duration <= time) {
                dp[i][time] = Math.max(dp[i][time], value + dp[i - 1][time - task.duration]);
            }
        }
    }

    const selectedTasks = [];
    let time = timeLimit;
    for (let i = n; i >= 1; i--) {
        if (dp[i][time] !== dp[i - 1][time]) {
            const task = sourceTasks[i - 1];
            selectedTasks.push(task);
            time -= task.duration;
        }
    }

    selectedTasks.reverse();
    return { bestValue: dp[n][timeLimit], selectedTasks };
}

function showResults(title, lines) {
    const resultBox = document.getElementById("resultBox");
    resultBox.innerHTML = `<p class="result-title">${title}</p>`;

    lines.forEach(line => {
        const paragraph = document.createElement("p");
        paragraph.className = "result-line";
        paragraph.innerHTML = `<span class="result-highlight">${line}</span>`;
        resultBox.appendChild(paragraph);
    });
}

render();
