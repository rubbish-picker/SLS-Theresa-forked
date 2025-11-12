package Theresa.card.skill;

import Theresa.action.DrawSilkAction;
import Theresa.action.RandomSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlowerSilk extends AbstractTheresaCard {
    public static final String ID = "theresa:FlowerSilk";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FlowerSilk() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        SilkPatch.setSilkForPreview(this,new NormalSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawSilkAction(magicNumber));
    }

    @Override
    public void triggerWhenBecomeDust() {
        this.flash();
        addToBot(new RandomSilkAction(new NormalSilk(),false,true));
    }

    @Override
    public void triggerWhenNoLongerDust() {
        this.flash();
        addToBot(new RandomSilkAction(new NormalSilk(),false,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}


