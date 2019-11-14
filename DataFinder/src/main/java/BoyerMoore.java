import static java.lang.Math.max;

class BoyerMoore {
    private static int NUM_OF_CHARS = 256;
    private int[] shifts;
    private char[] toFind;
    private char[] stock;
    private boolean isFirstBuffer;

    BoyerMoore(String toFind) {
        this.toFind = toFind.toCharArray();
        shifts = new int[NUM_OF_CHARS];
        if (toFind.length() == 0) {
            stock = new char[0];
        } else {
            stock = new char[this.toFind.length - 1];
        }
        isFirstBuffer = true;
        createShiftTable();
    }

    boolean find(char[] buffer) {
        if (!isFirstBuffer && String.copyValueOf(toFind).contains(String.copyValueOf(stock))) {
            return contains(addAll(stock, buffer));
        } else {
            return contains(buffer);
        }
    }

    private boolean contains(char[] buffer) {
        var textLength = buffer.length;
        var toFindLength = toFind.length;
        var s = 0;
        while (s <= (textLength - toFindLength)) {
            var j = toFindLength - 1;
            while (j >= 0 && toFind[j] == buffer[s + j]) {
                j--;
            }
            if (j < 0) {
                return true;
            } else {
                try {
                    if (buffer[s + j] > NUM_OF_CHARS) {
                        s += max(1, j + 1);
                    } else {
                        s += max(1, j - shifts[buffer[s + j]]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (var i = 0; i < toFind.length - 1; i++) {
            stock[stock.length - 1 - i] = buffer[buffer.length - 1 - i];
        }
        isFirstBuffer = false;
        return false;
    }

    private void createShiftTable() {
        for (var i = 0; i < NUM_OF_CHARS; i++) {
            shifts[i] = -1;
        }
        for (var i = 0; i < toFind.length; i++) {
            shifts[(int) toFind[i]] = i;
        }
    }

    private char[] addAll(char[] array1, char[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        var joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    private char[] clone(char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
}
