// class ListNode {
//     val: number
//     next: ListNode | null
//     constructor(val?: number, next?: ListNode | null) {
//         this.val = (val===undefined ? 0 : val)
//         this.next = (next===undefined ? null : next)
//     }
// }

function addTwoNumber(l1: ListNode | null, l2: ListNode | null,): ListNode | null{
    let head = new ListNode(0);
    let current = head;
    let carry = 0;

    while(l1 != null || l2 != null || carry != 0){
        let l1Val = l1 != null ? l1.val : 0;
        let l2Val = l2 != null ? l2.val : 0;
        
        let sum = carry + l1Val + l2Val;
        carry = Math.floor(sum / 10);
        current.next = new ListNode(sum % 10);
        current = current.next;
        
        if(l1 !== null) l1 = l1.next;
        if(l2 !== null) l2 = l2.next;
    }
    return head.next;
}