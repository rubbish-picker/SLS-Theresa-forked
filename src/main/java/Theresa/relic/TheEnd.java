package Theresa.relic;

import Theresa.action.RandomSilkAction;
import Theresa.helper.StringHelper;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.NormalSilk;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TheEnd extends CustomRelic {
    public static final String ID = "theresa:TheEnd";

    public TheEnd() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        addToBot(new RandomSilkAction(new NormalSilk(),false,true));
    }

    public void onTriggerSilk(AbstractCard c){
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,c, OtherEnum.Theresa_Dust);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

