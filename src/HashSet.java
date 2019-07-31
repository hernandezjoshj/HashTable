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
        if (find(value)) {
            return;
        }
        if (loadFactor() > 0.8) {
            HashSet tempHS = new HashSet(mTable.length * 2);
            tempHS.mTable = (Entry[]) Array.newInstance(Entry.class, mTable.length * 2);
            int m = mTable.length;
            int newM = tempHS.mTable.length;
            for (int i = 0; i < m; i++) {
                if (mTable[i] != null && !mTable[i].mIsNil) {
                    int hashCode = mTable[i].mValue.hashCode();
                    for (int j = 0; j < newM; j++) {
                        int probe = ((j * j) + j) / 2;
                        int hashIndex = Math.abs((hashCode + probe) % m);
                        if (tempHS.mTable[hashIndex] == null) {
                            Entry entry = new Entry();
                            entry.mValue = mTable[i].mValue;
                            entry.mIsNil = false;
                            tempHS.mTable[hashIndex] = entry;
                            break;
                        }
                    }
                }
            }
            mTable = tempHS.mTable;
        }

        int m = mTable.length;
        int hashCode = value.hashCode();
        int probe, hashIndex;

        for (int i = 0; i < m; i++) {
            probe = ((i*i)+i) / 2;
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

    public boolean find(ValueType value){
        int m = mTable.length;
        int hashCode = value.hashCode();
        int hashIndex = hashCode % mTable.length;
        int probe;

        try {
            if (mTable[hashIndex].mValue.equals(value)) {
                return true;
            }
        }

        catch (NullPointerException exception) {
            for (int i = 0; i < m; i++) {
                probe = ((i * i) + i) / 2;
                hashIndex = (hashCode + probe) % m;

                if (mTable[hashIndex] == null) {
                    return false;
                }

                if (mTable[hashIndex].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void remove(ValueType value) {
        int hashCode = value.hashCode();

        for (int i = 0; i < mTable.length; i++) {
            int probe = ((i*i)+i) / 2;
            int hashIndex = (hashCode + probe) % mTable.length;
            if (mTable[hashIndex].mValue.equals(value)) {
                mTable[hashIndex].mValue = null;
                mTable[hashIndex].mIsNil = true;
            }
        }

    }

    private int count() {
        mCount = 0;
        for (int i = 0; i < mTable.length; i++) {
            if (mTable[i] != null && !mTable[i].mIsNil) {
                mCount++;
            }
        }
        return mCount;
    }

    private double loadFactor() {
        int n = count();
        int m = mTable.length;
        return (double)n/m;
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