import java.lang.reflect.Array;

public class HashSet<ValueType> {
    private class Entry {
        public ValueType mValue;
        public boolean mIsNil;
    }

    private Entry[] mTable;
    private int mCount;

    public HashSet(int tableSize) {
        //adjust tableSize parameter if it is not a power of 2, so table length = 2^n
        if (!isPowerOf2(tableSize)) {
            tableSize = nextPowerOf2(tableSize);
        }
        mTable = (Entry[]) Array.newInstance(Entry.class, tableSize);
    }

    public void add(ValueType value) {
        int m, mTemp, hashCode, probe, hashIndex;

        //if value is already in the table, no need to resize nor add
        if (find(value)) {
            return;
        }

        //if #elements in the table take up more than 80% of the current table size, double the table size
        if (loadFactor() > 0.8) {
            m = mTable.length;
            Entry[] temp = (Entry[]) Array.newInstance(Entry.class, m * 2);
            mTemp = temp.length;

            //scan the original table from left to right and add existing elements to the new table
            for (int i = 0; i < m; i++) {
                if (mTable[i] != null) {
                    //when an element is found in the original table, hash it to the new table with
                    //the probing function to avoid collisions
                    for (int j = 0; j < mTemp; j++) {
                        hashCode = mTable[i].mValue.hashCode();
                        probe = ((j * j) + j) / 2;
                        hashIndex = Math.abs((hashCode + probe) % m);
                        //if the index of the new table is null, add the element to the new table
                        if (temp[hashIndex] == null) {
                            Entry entry = new Entry();          //todo:: figure out whether or not new entry is needed
                            entry.mValue = mTable[i].mValue;
                            entry.mIsNil = false;
                            temp[hashIndex] = entry;
                            break;
                        }
                    }
                }
            }
            //reassign the original table to point to the new table
            mTable = temp;
        }

        //continue with adding the new value into the table
        m = mTable.length;
        for (int i = 0; i < m; i++) {
            hashCode = value.hashCode();
            probe = ((i * i) + i) / 2;
            hashIndex = Math.abs((hashCode + probe) % m);

            if (mTable[hashIndex] == null) {
                Entry entry = new Entry();
                entry.mValue = value;
                entry.mIsNil = false;
                mTable[hashIndex] = entry;
                System.out.println(value);
                break;
            }
        }
    }

    //todo:: fix if condition
    public boolean find(ValueType value) {
        int m = mTable.length;
        int hashCode, probe, hashIndex;



        for (int i = 0; i < m; i++) {
            hashCode = value.hashCode();
            probe = ((i * i) + i) / 2;
            hashIndex = Math.abs((hashCode + probe) % m);

            //on first iteration, hashIndex is not affected; if the element is not at that index,
            //probing will continue to check
            if (mTable[hashIndex] != null && mTable[hashIndex].mValue.equals(value)) {
                return true;
            }
            //checks first to see if the index is empty, then checks the NIL flag for the case that
            //an element was previously there but was deleted


            if (mTable[hashIndex] == null) { // && !mTable[hashIndex].mIsNil) {  //todo:: fix this
                    return false;
                }
            }
        return false;
    }


    //todo:: fix reassignment
    public void remove(ValueType value) {
        int m = mTable.length;
        int hashCode, probe, hashIndex;

        for (int i = 0; i < m; i++) {
            hashCode = value.hashCode();
            probe = ((i * i) + i) / 2;
            hashIndex = Math.abs((hashCode + probe) % m);
            if (mTable[hashIndex] != null && mTable[hashIndex].mValue.equals(value)) {
                Entry entry = new Entry();
                entry.mIsNil = true;
                mTable[hashIndex] = entry;
            }
        }
    }



    ///auxiliary methods

    //returns number of elements currently in the table
    private int count() {
        mCount = 0;
        for (int i = 0; i < mTable.length; i++) {
            if (mTable[i] != null && !mTable[i].mIsNil) {
                mCount++;
            }
        }
        return mCount;
    }

    //returns the load factor of the table
    private double loadFactor() {
        int n = count();
        int m = mTable.length;
        return (double) n / m;
    }

    //returns whether or not N is a power of 2
    private boolean isPowerOf2(int n) {
        if (n == 0) {
            return false;
        }
        int ceil = (int) (Math.ceil((Math.log(n) / Math.log(2))));
        int floor = (int) (Math.floor((Math.log(n) / Math.log(2))));

        return ceil == floor;
    }

    //returns the closest power of 2 >= N
    private int nextPowerOf2(int n) {
        int temp = 1;
        while (temp <= n) {
            temp = temp << 1;
        }
        return temp;
    }

}