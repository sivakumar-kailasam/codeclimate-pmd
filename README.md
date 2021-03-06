# codeclimate-pmd
A code climate engine for running pmd on your java projects.

### Running changes in local
- `./tests/spec_for_checker`
- `./build.sh`
- go inside `tests/test_repo/fixture_code_base` and run `codeclimate analyse --dev` or `CODECLIMATE_DEBUG=1 codeclimate analyse --dev` for debug messages.

### Sample .codeclimate.yml configuration
```yaml
engines:
  pmd:
    enabled: true
    config: 'config/pmd/pmd.xml'
```

By default the engine runs the [code climate checker](https://github.com/sivakumar-kailasam/codeclimate-pmd/blob/master/config/codeclimate_pmd.xml) against your code if the config property is not defined


### Testing the checker
Run `./tests/spec_for_checker` which uses a [fixture project](https://github.com/sivakumar-kailasam/fixture_code_base) for running tests against the checker

To know more about different PMD rules visit [the PMD rulesets index](http://pmd.github.io/pmd-5.4.1/pmd-java/rules/index.html)

### Updating PMD (to be used after 5.5.0 is officially released)
- `cd bin`
- `./updatePmd.groovy`

### References
- https://docs.codeclimate.com/docs/building-a-code-climate-engine
- https://github.com/codeclimate/spec/blob/master/SPEC.md

