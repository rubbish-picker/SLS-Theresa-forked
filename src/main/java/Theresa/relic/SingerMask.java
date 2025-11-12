package Theresa.relic;

import Theresa.action.DelayActionAction;
import Theresa.action.RandomSilkAction;
import Theresa.helper.StringHelper;
import Theresa.silk.TearSilk;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SingerMask extends CustomRelic {
    public static final String ID = "theresa:SingerMask";

    boolean triggered = false;

    public SingerMask() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
        tips.add(new PowerTip(DESCRIPTIONS[1],DESCRIPTIONS[2]));
    }

    @Override
    public void atPreBattle() {
        triggered = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if(!triggered) {
            triggered = true;
            this.flash();
            addToBot(new DelayActionAction(new RandomSilkAction(new TearSilk(),false,true)));
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.type== AbstractCard.CardType.POWER){
            this.flash();
            addToBot(new RandomSilkAction(new TearSilk(),false,true));
        }
    }

    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 3)
        public static void Insert(AbstractMonster _inst, DamageInfo info) {
            if(info.type != DamageInfo.DamageType.NORMAL){
                AbstractRelic singerMask = AbstractDungeon.player.getRelic(ID);
                if(singerMask!=null){
                    info.output+=2;
                    singerMask.flash();
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


