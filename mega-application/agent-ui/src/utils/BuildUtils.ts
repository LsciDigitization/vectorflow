
const buildRuleMessage = (required: boolean, message: string) => {
  return [
    {
      required: required,
      message: message,
    }
  ]
};

export {
  buildRuleMessage
}
