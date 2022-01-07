package com.arrayqueue;

public class ArrayQueueDemo {



    // uesing a array to mock that the ArrayQueue how to work
    static class ArrayQueue {
        private int maxSize;
        private int front;
        private int rear;
        private int[] arr;

        //define constructor
        public ArrayQueue(int arrMaxSize) {
            maxSize = arrMaxSize;
            arr = new int[maxSize];
            front = -1;//指向queue头的前一个位置
            rear = -1;
        }

        //判断队列是否是满的
        //method
        public boolean isFull() {
            return rear == maxSize - 1;
        }

        //判断队列是否为空
        public boolean isEmpty() {
            return rear == front;
        }

        //添加数据到queue
        public void addQueue(int n) {
            if (isFull()){
                System.out.println("The Queue is full");
            }
            rear++;
            arr[rear] = n;
        }
        //获取队列数据，出队列
        public int deQueue(){
            if (isEmpty()) {
                //通过抛出异常
                throw new RuntimeException("队列空，不能取数据");
            }
            front++;// front 原本指向queue 第一个元素前一个 （-1）--> front 移动了，代表了真正删除
            return arr[front];
        }
        //显示队列数据
        public void showQueue(){
            if (isEmpty()){
                System.out.printf("队列为空");
                return;
            }
            for (int i = 0 ; i < arr.length; i++){
                System.out.println("element of Queue:"+ arr[i]);
            }
        }
        //显示队列的头数据（peek）
        public int peek(){
            if (isEmpty()){
                throw new RuntimeException("队列为空");
            }
            System.out.println(" "+ arr[front+1]);
            return arr[front+1];// front并没有真正做删减

        }
        //queue的长度
        public int size(){
            if(isEmpty()) return 0;
            else if (isFull()) return maxSize ;
            else{
                return rear - front ;
            }
        }

    }
    public  static void main(String[] args) {
        // test
        ArrayQueue test = new ArrayQueue(3);
        test.deQueue();
        test.peek();

        /*test.addQueue(2);
        test.addQueue(3);
        test.addQueue(3);
        test.addQueue(4);*/
        /*for (int i = 0 ; i < test.size(); i++) {
            System.out.println(" "+ test.deQueue());
        }*/
        //test.showQueue();


    }
}
