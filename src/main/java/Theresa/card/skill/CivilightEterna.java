package Theresa.card.skill;

import Theresa.action.CivilightAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.modifier.EternalMod;
import Theresa.patch.OtherEnum;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CivilightEterna extends AbstractTheresaCard {
    public static final String ID = "theresa:CivilightEterna";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public CivilightEterna() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        tags.add(OtherEnum.Theresa_Darkness);
        isEthereal = true;
        //CardModifierManager.addModifier(this,new EternalMod());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new CivilightAction(CardGroup.CardGroupType.HAND,magicNumber).setCopy(1,true,false).setCostDiff(-1).upgradeSource());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}



