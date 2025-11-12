package Theresa.silk;

import Theresa.action.MemorySilkAction;
import Theresa.helper.ImageHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MemorySilk extends AbstractSilk{
    public static final String ID = "theresa:MemorySilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public boolean isMemoried = false;

    public MemorySilk() {
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 1;
        this.amount = 1;
        this.img = ImageHelper.imgC;
        this.updateDescription();
    }

    @Override
    public boolean canSpreadAtTurnEnd(AbstractCard cardToSpread, boolean atTurnEnd) {
        return false;
    }

    @Override
    public void afterPlayed() {
        AbstractDungeon.actionManager.addToBottom(new MemorySilkAction(this.card,this));
        triggeredOnce();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public AbstractSilk makeCopy() {
        return new MemorySilk();
    }
}
