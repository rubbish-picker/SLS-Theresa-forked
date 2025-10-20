package Theresa.silk;

import Theresa.action.SilkFlashAction;
import Theresa.helper.ImageHelper;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class NormalSilk extends AbstractSilk{
    public static final String ID = "theresa:NormalSilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);


    public NormalSilk() {
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 3;
        this.amount = 3;
        this.img = ImageHelper.imgA;
        this.updateDescription();
    }

    @Override
    public void atTurnEnd(CardGroup.CardGroupType type) {
        if(type == CardGroup.CardGroupType.HAND || type == OtherEnum.Theresa_Dust){
            AbstractDungeon.actionManager.addToBottom(new SilkFlashAction(this));
            triggeredOnce();
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public boolean canReplace(AbstractSilk silkToReplace) {
        return silkToReplace.silkID == MindSilk.ID;
    }

    @Override
    public AbstractSilk makeCopy() {
        return new NormalSilk();
    }
}
