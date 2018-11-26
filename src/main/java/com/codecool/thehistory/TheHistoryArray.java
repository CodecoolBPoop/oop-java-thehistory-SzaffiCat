package com.codecool.thehistory;

import java.util.Arrays;

public class TheHistoryArray implements TheHistory {

    /**
     * This implementation should use a String array so don't change that!
     */
    private String[] wordsArray = new String[0];

    @Override
    public void add(String text) {
        //TODO: check the TheHistory interface for more information
        wordsArray = text.split("\\s+");
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        //TODO: check the TheHistory interface for more information
        for (int i = 0; i < wordsArray.length; ++i) {
            if (wordsArray[i].contentEquals(wordToBeRemoved)) {
                String[] destArray = new String[wordsArray.length - 1];
                System.arraycopy(wordsArray, 0, destArray, 0, i);
                System.arraycopy(wordsArray, i + 1, destArray, i, destArray.length - i);
                wordsArray = destArray;
            }
        }
    }

    @Override
    public int size() {
        //TODO: check the TheHistory interface for more information
        return wordsArray.length;
    }

    @Override
    public void clear() {
        //TODO: check the TheHistory interface for more information
        wordsArray = new String[0];
    }

    @Override
    public void replaceOneWord(String from, String to) {
        //TODO: check the TheHistory interface for more information
        for (int i = 0; i < wordsArray.length; i++) {
            if (wordsArray[i].equals(from)) {
                wordsArray[i] = to;
            }
        }
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        //TODO: check the TheHistory interface for more information
        int idx = 0;
        do {
            // find the next matching pattern
            idx = findNextMatch(wordsArray, idx, fromWords);

            // if idx is bigger than wordsArray's length minus the number of words in the fromWords that means
            // there is no room for another match -> we've finished
            if (idx <= wordsArray.length - fromWords.length) {
                // Expecting at least one element in both fromWords and toWords.
                // We use copyLen to replace the words instead of remove/insert to speed up the code
                int copyLen = Math.min(fromWords.length, toWords.length);
                // replace the words in the wordsArray
                System.arraycopy(toWords, 0, wordsArray, idx, copyLen);

                // if fromWords length is the same as toWords length, we already replaced all the words
                // with the previous arraycopy
                if (fromWords.length != toWords.length) {
                    // create a temporary array with the proper size and move unchanged data into it
                    // It's not the best idea to make a temp array and do the changes every time we have a match.
                    // One improvement idea is to not make the temp array here but store only some information which
                    // needed to do the changes after we found every match
                    String[] tempArray = new String[wordsArray.length + (toWords.length - fromWords.length)];
                    // copy the unchanged + already replaced part to the temp array
                    System.arraycopy(wordsArray, 0, tempArray, 0, idx + copyLen);

                    // insert the rest of the toWords into the output array if there are any
                    if (copyLen < toWords.length) {
                        System.arraycopy(toWords, copyLen, tempArray, idx + copyLen, toWords.length - copyLen);
                    }

                    // and copy the rest unmodified part of the array
                    System.arraycopy(wordsArray, idx + fromWords.length, tempArray, idx + toWords.length,
                            wordsArray.length - (idx + fromWords.length));

                    // Have you noticed we don't need to take care of deletion?

                    // copy the output array back to the wordsArray
                    wordsArray = tempArray;

                    // step idx to avoid infinite loops when changing for ex.: 'Il' to 'New Il'
                    idx += toWords.length - 1;
                }
            }

            ++idx;
        } while (idx <= wordsArray.length - fromWords.length);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArray) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

    private int findNextMatch(String[] words, int startIndex, String[] fromWords) {
        int idx = startIndex;
        while (idx <= words.length - fromWords.length) {
            // if the first word matches, start an inner loop
            if (words[idx].contentEquals(fromWords[0])) {
                int currentIdx = idx + 1;
                int wordIdx = 1; // to get the next word from fromWords
                while (wordIdx < fromWords.length && words[currentIdx].contentEquals(fromWords[wordIdx])) {
                    ++currentIdx;
                    ++wordIdx;
                }
                // if all the words are matching, break the outer loop and return with the index
                if (wordIdx == fromWords.length) break;
            }
            ++idx;
        }
        return idx;
    }
}
