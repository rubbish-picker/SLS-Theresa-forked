package Theresa.card.power;

import Theresa.action.DustAllSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.SilkPower;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WeaveTomorrow extends AbstractTheresaCard {
    public static final String ID = "theresa:WeaveTomorrow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public WeaveTomorrow() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        SilkPatch.setSilkForPreview(this,new NormalSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DustAllSilkAction(new NormalSilk()));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new SilkPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}





