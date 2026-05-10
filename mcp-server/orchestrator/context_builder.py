from tools.budget_tool import get_budget_limits
from tools.transaction import fetch_transactions


def build_context(user_id: int, query: str):
    transactions = fetch_transactions(user_id)
    budgets = get_budget_limits(user_id)
    return {
        "query": query,
        "transactions": transactions,
        "budgets": budgets
    }