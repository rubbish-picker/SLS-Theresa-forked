package Theresa.card.attack;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.HatePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhereIsHome extends AbstractTheresaCard {
    public static final String ID = "theresa:WhereIsHome";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public WhereIsHome() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 14;
        this.baseMagicNumber = magicNumber = 1;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.damage*2);
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,randomNumber,damageTypeForTurn),attackEffect));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HatePower(abstractPlayer,magicNumber),magicNumber));
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()) {
                int randomNumber2 = AbstractDungeon.cardRandomRng.random(0, this.magicNumber*2);
                addToBot(new ApplyPowerAction(mo,abstractPlayer,new HatePower(mo,randomNumber2),randomNumber2));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
    }
}
