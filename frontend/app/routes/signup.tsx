import { useState } from 'react'
import type { Route } from './+types/signup'
import Field from '~/components/field'

import './signup.css'
import { ButtonPrimary } from '~/components/button'

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
    const [confirmPassword, setConfirmPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState<{
        email: string
        password: string
    } | null>(null)

    const emailHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
        setErrorMessage(null)
    }

    const passwordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
        setErrorMessage(null)
    }

    const confirmPasswordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setConfirmPassword(e.target.value)
        setErrorMessage(null)
    }

    const submitHandler = (e: React.ChangeEvent<HTMLFormElement>) => {
        e.preventDefault()

        const errors = { email: '', password: '' }

        if (email.length < 2 || email.length > 100 || !email.includes('@')) {
            errors['email'] = 'Invalid email address'
        }

        if (password.length < 12) {
            errors['password'] =
                'Password length must be at least 12 characters'
        }

        if (password !== confirmPassword) {
            errors['password'] = 'Passwords do not match'
        }

        if (errors.email || errors.password) {
            setErrorMessage(errors)
            return
        }

        console.log(email, password, confirmPassword)
    }

    return (
        <div className="contents login">
            <div className="formContainer">
                <form onSubmit={submitHandler}>
                    <ErrorBlock errorMessage={errorMessage} />
                    <Field
                        // ref={ref}
                        value={email}
                        onChange={emailHandler}
                        placeholder={'Email address'}
                    />
                    <Field
                        // ref={ref}
                        value={password}
                        onChange={passwordHandler}
                        placeholder={'Password'}
                        type="password"
                    />
                    <Field
                        // ref={ref}
                        value={confirmPassword}
                        onChange={confirmPasswordHandler}
                        placeholder={'Confirm password'}
                        type="password"
                    />
                    <ButtonPrimary type="submit">Submit</ButtonPrimary>
                </form>
            </div>
        </div>
    )
}

type ErrorProps = { errorMessage: { email?: string; password?: string } | null }

function ErrorBlock({ errorMessage }: ErrorProps) {
    if (!errorMessage || (!errorMessage.email && !errorMessage.password)) {
        return ''
    }

    let message = ''

    if (errorMessage.email) {
        message = errorMessage.email
    } else if (errorMessage.password) {
        message = errorMessage.password
    }

    return <p>{message}</p>
}
