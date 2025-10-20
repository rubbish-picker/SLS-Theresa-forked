package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.HatePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InFire extends AbstractTheresaCard {
    public static final String ID = "theresa:InFire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public InFire() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF_AND_ENEMY);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new HatePower(abstractMonster,upgraded?2:1),upgraded?2:1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HatePower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(2);
        }
    }
}




