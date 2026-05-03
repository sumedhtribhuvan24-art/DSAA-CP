class Task {
    int id;
    String name;
    String category;
    int priority;
    int duration;
    String location;
    int value;

    Task(int id, String name, String category, int priority, int duration, String location, int value) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.duration = duration;
        this.location = location;
        this.value = value;
    }

    Task(Task other) {
        this(other.id, other.name, other.category, other.priority, other.duration, other.location, other.value);
    }

    Task copy() {
        return new Task(this);
    }

    @Override
    public String toString() {
        return "#" + id + " " + name + " [" + category + ", priority " + priority + ", " + duration + " mins, " + location + "]";
    }
}
