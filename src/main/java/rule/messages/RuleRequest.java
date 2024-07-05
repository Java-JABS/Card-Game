package rule.messages;

import rule.CardsSuit;

public class RuleRequest extends ClientRequest {
    private CardsSuit rule;

    public RuleRequest() {
        super(ClientRequestType.RULE);
    }

    public RuleRequest(CardsSuit cardsSuit) {
        this();
        this.rule = cardsSuit;
    }

    public CardsSuit getRule() {
        return rule;
    }

    public void setRule(CardsSuit rule) {
        this.rule = rule;
    }
}
