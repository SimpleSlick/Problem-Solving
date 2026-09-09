class Solution {
    fun lengthofSubString(str: String): Int{
        var result = 0
        var i = 0
        var j = 0

        var hashMap = HashMap<Char,Int>()

        while(j < str.length){
            val ch = str[j]

            if(hashMap.containsKey(ch)){
                i = Math.max(hashMap[ch]!!, i)
            }

            result = Math.max(result, j - i + 1)
            hashMap[ch] = j + 1

            j++;
        }

        return result
    }
}