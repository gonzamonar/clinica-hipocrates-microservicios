export function getRandom<T>(array: T[]): T {
  return array[Math.floor(Math.random() * array.length)];
}

export function getRandomNumber(min: number, max: number): number {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export function getRandomUniqueNumbers(min: number, max: number, count: number): number[] {
  const numbers = new Set<number>();
  while (numbers.size < count) {
    const n = Math.floor(Math.random() * (max - min + 1)) + min;
    numbers.add(n);
  }
  return Array.from(numbers);
}

export function getRandomFutureDate(): Date {
  const now = new Date();
  const daysToAdd = Math.floor(Math.random() * 90) + 1;
  const date = new Date(now);
  date.setDate(date.getDate() + daysToAdd);
  while (date.getDay() === 0) {
    date.setDate(date.getDate() + 1); // Skip Sunday
  }

  const day = date.getDay();
  let startHour = 8;
  let endHour = day === 6 ? 14 : 19; // Saturday shorter hours

  // Generate valid 30-minute slots
  const timeSlots: { hour: number; minute: number }[] = [];
  for (let h = startHour; h < endHour; h++) {
    timeSlots.push({ hour: h, minute: 0 });
    timeSlots.push({ hour: h, minute: 30 });
  }

  // Remove the 30-minute slot at closing hour (19:00 & 14:00 not allowed)
  timeSlots.pop();

  // Pick a random slot
  const { hour, minute } = timeSlots[Math.floor(Math.random() * timeSlots.length)];

  // Set final date
  date.setHours(hour, minute, 0, 0);
  return date;
}