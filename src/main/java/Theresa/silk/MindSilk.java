package Theresa.silk;

import Theresa.action.SilkFlashAction;
import Theresa.helper.ImageHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MindSilk extends AbstractSilk {
    public static final String ID = "theresa:MindSilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public boolean atOnce = false;

    public MindSilk() {
       this(false);
    }

    public MindSilk(boolean atOnce){
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 1;
        this.amount = 1;
        this.img = ImageHelper.imgD;
        this.updateDescription();
    }

    @Override
    public void onCopied() {
        if(atOnce){
            atOnce = false;
            AbstractDungeon.actionManager.addToTop(new SilkFlashAction(this));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new SilkFlashAction(this));
        triggeredOnce();
    }

    @Override
    public boolean canReplace(AbstractSilk silkToReplace) {
        if(silkToReplace.silkID == NormalSilk.ID)
            return true;
        return false;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public AbstractSilk makeCopy() {
        return new MindSilk();
    }
}
