package com.arrayqueue;

public class cricleArray {
     private int maxSize;
     private int front;
     private int rear;
     private int[] arr;
     // define constructor
     public cricleArray(int arrMaxSize){
         maxSize = arrMaxSize;
         front = 0;
         rear = 0;
         arr = new int[maxSize];
     }
     // check the queue is full or not
     public boolean isFull(){
         return (rear+1) % maxSize == front;
     }
     // check the queue is empty or not
     public boolean isEmpty(){
         return rear == front;
     }
     // add a element in queue
     public void enqueue(int n){
         if(isFull()) {
             System.out.println("The queue is full");
         }
         arr[rear] = n;
         rear = (rear + 1) % maxSize;
     }
     // delete a element in queue
     public int pop(){
         if (isEmpty()){
             throw new RuntimeException("the queue is empty");
         }
         int cur = arr[front];
         //front后移考虑取模
         //因为是circle,所以rear or front后移都需要取模
         front = (front + 1) % maxSize;
         return cur;
         //这样子可以保证front永远指向queue的第一个元素
     }

}
