class Solution{
    lengthOfSubstring(s:string): number {
        let result = 0
        const hashMap = new Map<string, number>();

        let i = 0;
        let j = 0;

        while(j < s.length){
            const ch: any = s[j];

            if(hashMap.has(ch)){
                i = Math.max(hashMap.get(ch)!, i);
            }

            result = Math.max(result, j - i + 1);
            hashMap.set(ch, j + 1);

            j++;
        }

        return result;
    }
}