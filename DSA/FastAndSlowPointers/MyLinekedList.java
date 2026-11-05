package DSA.FastAndSlowPointers;

public class MyLinekedList {
    Node head;

    void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    void print() {
        if (head == null) {
            System.out.println("MyLinkedList is Empty");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    void removeFirst() {
        if (head == null) {
            System.out.println("Nothing to remove");
            return;
        }
        head = head.next;
    }

    void removeLast() {
        if (head == null) {
            System.out.println("No Elements tp remove");
            return;
        }
        if (head.next == null) {
            head = null;
            return;
        }
        Node current = head;
        while (current != null) {
            if (current.next.next == null) {
                current.next = null;
                return;
            }
            current = current.next;
        }
    }

    void removeValue(int value) {
        if (head == null) {
            System.out.println("No elements to remove");
            return;
        }
        if (head.data == value) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current != null) {
            if (current.next != null && current.next.data == value) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    void createCyclic() {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = head;
    }

    boolean hasCyclic() {
        boolean hasCyclic = false;
        if (head == null) {
            return hasCyclic;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCyclic = true;
                return hasCyclic;
            }
        }
        return hasCyclic;
    }

    int cyclicLength() {
        if (head == null) {
            return 0;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                Node current = slow;
                int counter = 0;
                current = current.next;
                counter++;
                while (current != slow) {
                    current = current.next;
                    counter++;
                }
                return counter;
            }
        }
        return 0;
    }

    int findStartCycle() {
        if (head == null) {
            return 0;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                Node pointer = head;
                Node slowPointer = slow;
                while (pointer != slowPointer) {
                    pointer = pointer.next;
                    slowPointer = slowPointer.next;
                }
                return pointer.data;
            }
        }
        return 0;
    }

    int findMiddle(){
        Node slow = head;
        Node fast = head;
        while(fast!= null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    boolean isPallindrome(){
        if (head == null || head.next == null) {
            return true;
        }
        Node slow = head;
        Node fast = head;
        while(fast!= null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        Node current = slow;
        Node prev = null;
        while(current != null){
            Node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        Node left = head;
        Node right = prev;

        while(right != null){
            if(left.data != right.data){
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    public static void main(String[] args) {
        MyLinekedList list = new MyLinekedList();
        list.add(6);
        list.add(10);
        list.add(20);
        list.add(30);
//        list.add(20);
//        list.add(10);
//        list.add(6);
//        list.createCyclic();
//        System.out.println(list.hasCyclic());
//        System.out.println(list.cyclicLength());
//        System.out.println(list.findStartCycle());
//        System.out.println(list.findMiddle());
        System.out.println(list.isPallindrome());
//        list.print();
    }
}


class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;

    }
}

