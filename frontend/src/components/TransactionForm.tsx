function TransactionForm() {
    return (
        <form>
            <input type="text" placeholder="Enter description"/>
            <input type="number" placeholder="Enter amount"/>
            <button type="submit">Add transaction</button>
        </form>
    );
}

export default TransactionForm;