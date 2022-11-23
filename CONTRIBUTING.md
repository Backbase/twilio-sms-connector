## Contributing

Hi there! We're thrilled that you'd like to contribute to this project.

## Topics

* [Reporting Issues](#reporting-issues)
* [Working On Issues](#working-on-issues)
* [Contributing](#contributing)
* [Documentation](#documentation)
* [Submitting Pull Requests](#submitting-pull-requests)
* [Continuous Integration](#continuous-integration)
* [Communications](#communications)

## Reporting Issues
Before reporting an issue, check our backlog of [open issues][open-issues-url] to see if someone else has already reported it. If so, feel free to add your scenario, or additional information, to the discussion. Or simply "subscribe" to it to be notified when it is updated.

If you find a new issue with the project we'd love to hear about it! The most important aspect of a bug report is that it includes enough information for us to reproduce it. So, please include as much detail as possible and try to remove the extra stuff that doesn't really relate to the issue itself. The easier it is for us to reproduce it, the faster it'll be fixed!

Please don't include any private/sensitive information in your issue!

## Working On Issues
Once you have decided to contribute by working on an issue, check our backlog of [open issues][open-issues-url] looking for any that do not have an "In Progress" label attached to it. Often issues will be assigned to someone, to be worked on at a later time. If you have the time to work on the issue now add yourself as an assignee, and set the "In Progress" label if you’re a member of the “Backbase” GitHub organization. If you can not set the label, just add a quick comment in the issue asking that the “In Progress” label be set and a member will do so for you.

## Contributing
This section describes how to start a contribution to the project. These instructions are geared towards using a Linux/Mac development machine.

### Prepare your environment

Prerequisite tools to install

- Java 17

### Fork and clone the project

1. [Fork][fork-url] and clone the repository
2. Configure and install the project: `./mvnw clean package`
3. Make sure the tests pass on your machine: `./mvnw test`
4. Create a new branch: `git checkout -b feature/my-branch-name`
5. Make your change, add tests, and make sure the tests still pass
6. Commit and push to your fork and [submit a pull request][create-pr-url]
7. Pat your self on the back and wait for your pull request to be reviewed and merged.

Here are a few things you can do that will increase the likelihood of your pull request being accepted:

- Write tests.
- Keep your change as focused as possible. If there are multiple changes you would like to make that are not dependent upon each other, consider submitting them as separate pull requests.
- Write a [good commit message][good-commit-message-url].

## Documentation
Make sure to update the documentation if needed. The documentation can be found unser `README.md` and `docs/*`.

## Submitting Pull Requests
No Pull Request (PR) is too small! Typos, additional comments in the code,
new test cases, bug fixes, new features, more documentation, ... it's all
welcome!

While bug fixes can first be identified via an "issue", that is not required.
It's ok to just open up a PR with the fix, but make sure you include the same
information you would have included in an issue - like how to reproduce it.

PRs for new features should include some background on what use cases the
new code is trying to address. When possible and when it makes sense, try to break-up
larger PRs into smaller ones - it's easier to review smaller
code changes. But only if those smaller ones make sense as stand-alone PRs.

Regardless of the type of PR, all PRs should include:
* well documented code changes.
* additional testcases. Ideally, they should fail without your code change applied.
* documentation changes.

Squash your commits into logical pieces of work that might want to be reviewed
separate from the rest of the PRs. But, squashing down to just one commit is ok
too since in the end the entire PR will be reviewed anyway. When in doubt,
squash.

### Describe your Changes in Commit Messages

Describe your problem. Whether your patch is a one-line bug fix or 5000 lines
of a new feature, there must be an underlying problem that motivated you to do
this work. Convince the reviewer that there is a problem worth fixing and that
it makes sense for them to read past the first paragraph.

Once the problem is established, describe what you are actually doing about it
in technical detail. It’s important to describe the change in plain English for
the reviewer to verify that the code is behaving as you intend it to.

Solve only one problem per patch. If your description starts to get long,
that’s a sign that you probably need to split up your patch.

### Sign your PRs

The sign-off is a line at the end of the explanation for the patch. Your
signature certifies that you wrote the patch or otherwise have the right to pass
it on as an open-source patch. The rules are simple: if you can certify
the below (from [developercertificate.org](https://developercertificate.org/)):

```
Developer Certificate of Origin
Version 1.1

Copyright (C) 2004, 2006 The Linux Foundation and its contributors.
660 York Street, Suite 102,
San Francisco, CA 94110 USA

Everyone is permitted to copy and distribute verbatim copies of this
license document, but changing it is not allowed.

Developer's Certificate of Origin 1.1

By making a contribution to this project, I certify that:

(a) The contribution was created in whole or in part by me and I
    have the right to submit it under the open source license
    indicated in the file; or

(b) The contribution is based upon previous work that, to the best
    of my knowledge, is covered under an appropriate open source
    license and I have the right under that license to submit that
    work with modifications, whether created in whole or in part
    by me, under the same open source license (unless I am
    permitted to submit under a different license), as indicated
    in the file; or

(c) The contribution was provided directly to me by some other
    person who certified (a), (b) or (c) and I have not modified
    it.

(d) I understand and agree that this project and the contribution
    are public and that a record of the contribution (including all
    personal information I submit with it, including my sign-off) is
    maintained indefinitely and may be redistributed consistent with
    this project or the open source license(s) involved.
```

Then you just add a line to every git commit message:

    Signed-off-by: Joe Smith <joe.smith@email.com>

Use your real name (sorry, no pseudonyms or anonymous contributions.)

If you set your `user.name` and `user.email` git configs, you can sign your
commit automatically with `git commit -s`.

## Continuous Integration

All pull requests and branch-merges automatically run:

* Format/lint checking
* Unit testing
* Integration Testing

Your pull request will not be allowed to merge unless all tests are stable.

## Communications

For general questions and discussion, please use the
[GitHub discussions][discussions-url]

For discussions around issues/bugs and features, you can use the GitHub
[issues][open-issues-url]
tracking system.

[good-commit-message-url]: https://conventionalcommits.org
[fork-url]: https://github.com/backbase/twilio-sms-connector/fork
[create-pr-url]: https://github.com/backbase/twilio-sms-connector/compare
[open-issues-url]: https://github.com/backbase/twilio-sms-connector/issues
[discussions-url]: https://github.com/backbase/twilio-sms-connector/discussions
