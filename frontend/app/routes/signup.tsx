import { useState } from 'react'
import type { Route } from './+types/signup'

export function meta({}: Route.MetaArgs) {
    return [
        { title: 'Signup - OfCourse' },
        { name: 'description', content: 'Create an account for' },
    ]
}

export default function Signup() {
    return (
        <div>
            <h1>Signup</h1>
            <SignupBox />
        </div>
    )
}

function SignupBox() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState(null)

    const emailHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
        setErrorMessage(null)
    }

    const passwordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
        setErrorMessage(null)
    }

    return (
        <div>
            <input
                // ref={ref}
                className={'field'}
                value={email}
                onChange={emailHandler}
                placeholder={'Email address'}
            />
            <input
                // ref={ref}
                className={'field'}
                value={password}
                onChange={passwordHandler}
                placeholder={'Password'}
                type="password"
            />
        </div>
    )
}
