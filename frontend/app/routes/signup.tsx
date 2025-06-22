import { useState } from 'react'
import type { Route } from './+types/signup'
import Field from '~/components/field'

import './signup.css'
import { ButtonPrimary } from '~/components/button'
import { Link, useNavigate } from 'react-router'

interface ErrorMessage {
    email: string
    password: string
}

export function meta({}: Route.MetaArgs) {
    return [
        { title: 'Signup - OfCourse' },
        { name: 'description', content: 'Create an account for OfCourse' },
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
    const [errorMessage, setErrorMessage] = useState<ErrorMessage | null>(null)
    const navigate = useNavigate()

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

        if (password !== confirmPassword) {
            errors['password'] = 'Passwords do not match'
        }

        if (password.length < 12) {
            errors['password'] =
                'Password length must be at least 12 characters'
        }

        if (errors.email || errors.password) {
            setErrorMessage(errors)
            return
        }

        console.log(email, password, confirmPassword)
        // TODO send to server
        // TODO recieve & save JWT
        navigate('/')
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
                    <div style={{ marginTop: '20px' }}>
                        <ButtonPrimary className="formSubmit" type="submit">
                            Sign up
                        </ButtonPrimary>
                    </div>
                    <div className="linkContainer">
                        <Link to="/login" className="link">
                            Have an account? Log in
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    )
}

function ErrorBlock({ errorMessage }: { errorMessage: ErrorMessage | null }) {
    if (!errorMessage || (!errorMessage.email && !errorMessage.password)) {
        return ''
    }

    let message = ''

    if (errorMessage.email) {
        message = errorMessage.email
    } else if (errorMessage.password) {
        message = errorMessage.password
    }

    return <p className="error message">{message}</p>
}
