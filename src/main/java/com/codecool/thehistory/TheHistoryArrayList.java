package com.codecool.thehistory;

import java.util.*;

public class TheHistoryArrayList implements TheHistory {
    /**
     * This implementation should use a String ArrayList so don't change that!
     */
    private List<String> wordsArrayList = new ArrayList<>();

    @Override
    public void add(String text) {
        // the same regexp but it's output converted so it can be put into our ArrayList
        Collections.addAll(wordsArrayList, text.split("\\s+"));
    }
    /*public void add(String text) {
        //TODO: check the TheHistory interface for more information
        wordsArrayList = Arrays.asList(text.split("\\s+"));
    }*/

    @Override
    public void removeWord(String wordToBeRemoved) {
        //TODO: check the TheHistory interface for more information
        for (int i = 0; i < wordsArrayList.size(); i++) {
            if (wordToBeRemoved.equals(wordsArrayList.get(i))) {
                wordsArrayList.remove(i);
                i--;
            }
        }
    }

    @Override
    public int size() {
        //TODO: check the TheHistory interface for more information
        return wordsArrayList.size();
    }

    @Override
    public void clear() {
        wordsArrayList.clear();
    }

    @Override
    public void replaceOneWord(String from, String to) {
        //TODO: check the TheHistory interface for more information
        ListIterator<String> iterator = wordsArrayList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().contentEquals(from)) {
                iterator.set(to);
            }
        }
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        //TODO: check the TheHistory interface for more information
        int idx = 0;
        do {
            // let's find the next match. If there are no more matches, idx will be 'too big'
            idx = findNextMatch(idx, fromWords);
            if (idx <= wordsArrayList.size() - fromWords.length) {
                // Expecting at least one element in both fromWords and toWords (as before).
                // We use copyLen to replace the words instead of remove/insert to speed up the code
                int copyLen = Math.min(fromWords.length, toWords.length);
                // replace elements. I used the set() method just to show ArrayList has it :)
                for (int i = 0; i < copyLen; ++i) {
                    wordsArrayList.set(idx + i, toWords[i]);
                }
                // if there are more things to do..
                if (fromWords.length != toWords.length) {
                    if (fromWords.length < toWords.length) {
                        // Addition: add rest of the elements to our ArrayList
                        for (int i = copyLen; i < toWords.length; ++i) {
                            wordsArrayList.add(idx + i, toWords[i]);
                        }
                        // make sure we don't iterate over these elements again ( ==> increase idx)
                        idx += toWords.length - copyLen;
                    } else {
                        // Deletion: skip the replaced elements (copyLen) and remove the rest
                        // This way we (seemingly) don't need a temporary array. In reality it is not that simple
                        // because ArrayList may create temporary arrays in the background sometimes
                        for (int i = copyLen; i < fromWords.length; ++i) {
                            wordsArrayList.remove(idx + 1);
                        }
                    }
                }
            }

            ++idx;
        } while (idx <= wordsArrayList.size() - fromWords.length);
    }

    private int findNextMatch(int startIndex, String[] fromWords) {
        // this one is the same as in TheHistoryArray
        int idx = startIndex;
        while (idx <= wordsArrayList.size() - fromWords.length) {
            if (wordsArrayList.get(idx).contentEquals(fromWords[0])) {
                int currentIdx = idx + 1;
                int wordIdx = 1;
                while (wordIdx < fromWords.length && wordsArrayList.get(currentIdx).contentEquals(fromWords[wordIdx])) {
                    ++currentIdx;
                    ++wordIdx;
                }
                if (wordIdx == fromWords.length) break;
            }
            ++idx;
        }
        return idx;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArrayList) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

}
