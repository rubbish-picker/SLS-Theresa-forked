package Theresa.power.buff;

import Theresa.action.DustToPileAction;
import Theresa.action.UpdateTurnCostAction;
import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IntoHistoryPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:IntoHistoryPower";

    public IntoHistoryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    public void triggerCard(AbstractCard c) {
        this.flashWithoutSound();
        addToBot(new DustToPileAction(c, CardGroup.CardGroupType.HAND));
        addToBot(new UpdateTurnCostAction(c, AbstractDungeon.player.hand,-amount));
    }
}
