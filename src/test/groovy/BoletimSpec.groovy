import spock.lang.Specification

class BoletimSpec extends Specification {

    private Script loadScript() {
        return new GroovyShell().parse(new File(System.getProperty('user.dir'), 'src/main/groovy/Boletim.groovy'))
    }

    def 'bug 1: calcula a média incluindo todas as notas'() {
        given:
        def script = loadScript()

        expect:
        Math.abs(script.calcularMedia([8.0, 7.5, 9.0]) - 8.166666666666666) < 0.0000001
        Math.abs(script.calcularMedia([5.0, 6.0, 7.0]) - 6.0) < 0.0000001
        Math.abs(script.calcularMedia([10.0, 4.0, 7.0]) - 7.0) < 0.0000001
    }

    def 'bug 2: usa o limite correto para recuperacao'() {
        given:
        def script = loadScript()

        expect:
        script.situacao(4.9, 7.0, 5.0) == 'Reprovado'
        script.situacao(5.0, 7.0, 5.0) == 'Recuperação'
        script.situacao(6.0, 7.0, 5.0) == 'Recuperação'
        script.situacao(6.9, 7.0, 5.0) == 'Recuperação'
    }

    def 'bug 3: considera o valor exato da nota de corte da aprovacao'() {
        given:
        def script = loadScript()

        expect:
        script.situacao(6.9, 7.0, 5.0) == 'Recuperação'
        script.situacao(7.0, 7.0, 5.0) == 'Aprovado'
        script.situacao(8.0, 7.0, 5.0) == 'Aprovado'
    }
}
