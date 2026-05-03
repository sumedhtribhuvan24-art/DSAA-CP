Smart Task Manager - DSA Project

Project Idea:
This project shows how different data structures and algorithms can solve real problems inside one task manager.
Instead of only adding tasks on screen, the backend demonstrates how tasks can be managed, searched, prioritized, optimized, and routed intelligently.

Implemented DSA Concepts:
1. Stack
Used for undo and redo of task operations.
Real life example: if a user deletes a task by mistake, the last action can be reversed quickly.

2. Heap / Priority Queue
Used to schedule urgent tasks before normal tasks.
Real life example: "Submit lab record" should come before "Buy groceries" if its priority is higher.

3. HashMap
Used for fast lookup of tasks using task ID.
Real life example: opening task 103 directly without scanning the full list.

4. Trie
Used for prefix-based task suggestions.
Real life example: when the user types "Pr", the app suggests "Prepare DSA presentation".

5. Graph with Dijkstra
Used to find the shortest route between locations where tasks must be completed.
Real life example: finding the best route from Home to Faculty Block while visiting campus places.

6. Dynamic Programming using Knapsack
Used to choose the best combination of tasks when available time is limited.
Real life example: selecting the most important tasks that fit inside 3 hours.

Time Complexity:
Stack -> O(1)
HashMap -> O(1) average lookup
Heap -> O(log n)
Trie -> O(L) for insert/search where L is word length
Dijkstra -> O(E log V)
Knapsack -> O(nW)

How To Explain In Viva:
We designed a smart task manager where each data structure solves one real problem.
Stack handles undo and redo, heap handles task priority, HashMap gives fast lookup, Trie gives search suggestions, graph finds shortest path, and dynamic programming helps choose the best tasks in less time.

Files:
frontend/
Simple website interface for task entry and presentation.

backend/
Java implementation of all DSA concepts.

How To Run:
1. Open frontend/index.html in browser.
2. Compile Java files inside backend using: javac *.java
3. Run the backend demo using: java Main
