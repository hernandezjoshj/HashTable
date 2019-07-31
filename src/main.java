public class main {

    public static void main(String[] args) {

        HashSet hashSet = new HashSet(5);
        hashSet.add(0); //1
        hashSet.add(1); //2
        hashSet.add(2); //3
        hashSet.add(4); //4
        hashSet.add(3); //5
        hashSet.add(5); //6
        hashSet.add(6); //7
        hashSet.add(7); //8

        System.out.println(hashSet.find(4));
        System.out.println(hashSet.find(3));
        System.out.println(hashSet.find(5));

    }


}
