class Solution {
    intToRoman(num: number): string {
        const thousands: string[] = ["", "M", "MM", "MMM"];
        const hundreds: string[] = ["", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"];
        const tens: string[] = ["", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"];
        const ones: string[] = ["", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"];

        return (
            thousands[Math.floor(num / 1000)] +
            hundreds[Math.floor((num % 1000) / 100)] +
            tens[Math.floor((num % 100) / 10)] +
            ones[num % 10]
        );
    }
}