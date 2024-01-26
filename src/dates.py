from datetime import datetime, timedelta


def days_until(target_date: datetime) -> int:

    current_date = datetime.now()
    days_difference = (target_date - current_date).days
    
    return days_difference + 1


def is_this_week(target_date: datetime ) -> bool:

    current_date = datetime.now()

    start_of_week = current_date - timedelta(days=current_date.weekday())
    end_of_week = start_of_week + timedelta(days=6)

    return start_of_week <= target_date <= end_of_week
