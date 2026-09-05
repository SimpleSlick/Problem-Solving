//  var li = ListNode(5)
//  var v = li.`val`
//
//  class ListNode(var `val`: Int) {
//      var next: ListNode? = null
//  }

class Solution {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val head = ListNode(0)
        var current = head

        var p1 = l1
        var p2 = l2
        var carry = 0

        while(p1 != null || p2 != null || carry != 0) {
            val x = p1?.`val` ?: 0
            val y = p2?.`val` ?: 0

            val sum = carry + x + y
            carry = sum / 10

            current.next = ListNode(sum % 10)
            current = current.next!!

            p1 = p1?.next
            p2 = p2?.next
        }
        return head.next
    }
}