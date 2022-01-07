package com.arrayqueue;

public class ArrayQueue {

    static class arrayQueue {
        private int maxSize;
        private int front;
        private int rear;
        private int[] arr;
        // define constructor
        public arrayQueue(int arrMaxSize){
            maxSize = arrMaxSize;
            front = -1;
            rear = -1;
            arr = new int[maxSize];
        }
        // check the queue is full or not
        public boolean isFull(){
            return rear == maxSize - 1;
        }

        // check the queue is empty or not
        public boolean isEmpty(){
            return rear == front;
        }
        // push elements to queue
        public void enQueue(int n){
            if (isFull()) {
                System.out.println("The queue is full, cannot add the element");
                return;
            }
            rear++;
            arr[rear] = n;
        }
        //pop the elements from top of queue
        public int dequeue(){
            if (isEmpty()){
                throw new RuntimeException("The queue is Empty");
            }
            front++;
            return  arr[front];
        }
        //get the top of element of the Queue
        public int peek(){
            if (isEmpty()){
                throw new RuntimeException("The queue is Empty");
            }
            return arr[front+1];
        }
        // show the element of the Queue
        public void show(){
            if (isEmpty()){
                System.out.println("The queue is empty");
                return;
            }
            for (int i = 0 ; i < arr.length; i++){
                System.out.println(" "+ arr[i]);
            }
        }
        // size of the queue
        public int size(){
            if (isEmpty()) return 0;
            else if (isFull()) return maxSize;
            else{
                return rear - front;
            }
        }
        public static void main(String[] args) {
            //test
            arrayQueue test = new arrayQueue(3);
            test.enQueue(3);
            test.enQueue(9);
            test.enQueue(2);
            test.show();
            int len = test.size();
            System.out.println("len:"  + len);
            int pop = test.dequeue();
            System.out.println("pop :" + pop);
            int peek = test.peek();
            System.out.println("peek :" + peek);
        }
    }
}
