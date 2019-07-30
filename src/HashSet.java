import java.lang.reflect.Array;

public class HashSet<ValueType> {
    private class Entry {
        public ValueType mValue;
        public boolean mIsNil;
    }

    private Entry[] mTable;
    private int mCount;

    public HashSet(int tableSize) {
        if (!isPowerOf2(tableSize)) {
            tableSize = nextPowerOf2(tableSize);
        }

        mTable = (Entry[])Array.newInstance(Entry.class, tableSize);

    }

    public void add(ValueType value) {
        int m = mTable.length;
        int hashCode, probe, hashIndex;

        for (int i = 0; i < m; i++) {
            hashCode = value.hashCode();
            probe = ((i*i)+i) / 2;
            hashIndex = (hashCode + probe) % m;
            if (mTable[hashIndex] == null) {
                Entry entry = new Entry();
                entry.mValue = value;
                entry.mIsNil = false;
                mTable[hashIndex] = entry;
                break;
            }
        }

    }

    // Returns true if the given value is present in the set.
    public boolean find(ValueType value){
        int hashIndex = value.hashCode() % mTable.length;
        
        if (mTable[hashIndex].mValue.equals(value)) {
            return true;
        }

        for (int i = 0; i < mTable.length; i++) {
            int probe = ((i*i)+i) / 2;
            hashIndex = (value.hashCode() + probe) % mTable.length;

            if (mTable[hashIndex] == null) {
                return false;
            }

            if (mTable[hashIndex].equals(value)) {
                return true;
            }
        }
        return false;
    }

    // Removes the given value from the set.
    public void remove(ValueType value) {
    }

    private int count() {
        mCount = 0;
        for (int i = 0; i < mTable.length; i++) {
            if (mTable[i].mValue != null && !mTable[i].mIsNil) {
                mCount++;
            }
        }
        return mCount;
    }

    private double loadFactor() {
        int n = mCount;
        int m = mTable.length;
        return n/m;
    }

    private void resize() {

    }

    private boolean isPowerOf2 (int n) {
        if (n == 0) {
            return false;
        }
        int ceil = (int) (Math.ceil((Math.log(n) / Math.log(2))));
        int floor = (int) (Math.floor((Math.log(n) / Math.log(2))));

        return ceil == floor;
    }

    private int nextPowerOf2(int n) {
        int temp = 1;
        while (temp <= n) {
            temp = temp << 1;
        }
        return temp;
    }

}