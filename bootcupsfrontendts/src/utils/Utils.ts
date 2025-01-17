export function firstLetterToLowercase(str: string): string {
  return str.charAt(0).toLowerCase() + str.slice(1);
}

export function checkAll(
  checkAll: HTMLInputElement, 
  checkboxes: Array<HTMLInputElement>
): number {
  const isChecked = checkAll.checked;

  for (let i = 0; i < checkboxes.length; ++i) {
    checkboxes[i].checked = isChecked;
  }

  if (isChecked) return checkboxes.length;
  else return 0;
}